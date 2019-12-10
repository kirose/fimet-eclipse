package com.fimet.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.keys.IBindingService;

import com.fimet.commons.Activator;
import com.fimet.core.IBindingManager;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.impl.commands.parser.ParserCmd;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 * This class configure short cuts in BindingManager
 */
@SuppressWarnings("restriction")
public class BindingManager implements IBindingManager {
	/**
	 * Stores IHandlerActivation reference for probable deactivation
	 */
	private Map<String, IHandlerActivation> cmdHandlers = new HashMap<>();  
	/**
	 * The handler service
	 */
	private IHandlerService handlerService = PlatformUI.getWorkbench().getService(IHandlerService.class);//org.eclipse.ui.internal.handlers.LegacyHandlerService
	/**
	 * The binding service
	 */
	private BindingService bindingService = (BindingService)PlatformUI.getWorkbench().getService(IBindingService.class);//IBindingService bservice
	/**
	 * The Command service for registre commands
	 */
	private ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);//org.eclipse.ui.internal.commands.CommandService
	/**
	 * Binnding Manager for key binding (short-cuts)
	 */
	private org.eclipse.jface.bindings.BindingManager bindingManager = bindingService.getBindingManager();
	
	private static final String COMMAND_BASE_ID = "com.fimet.commands.parser.";
	private static final String DEFAULT_SCHEME = "org.eclipse.ui.defaultAcceleratorConfiguration";
	private static final String CONTEXT_TEXT_EDITOR = "org.eclipse.ui.textEditorScope";
	private static final String CATEGODY_PARSER = "com.fimet.parser";
	
	/**
	 * 
	 */
	private Binding[] removeBindings(String commandId) {
		
		Binding[] bindings = bindingManager.getBindings();
		List<Binding> matches = new ArrayList<>();
		if (bindings != null && bindings.length > 0) {
			for (Binding binding : bindings) {
				if (
					binding.getParameterizedCommand() != null && 
					binding.getParameterizedCommand().getCommand() != null &&
					binding.getParameterizedCommand().getCommand().getId().equals(commandId)
				) {
					bindingManager.removeBinding(binding);
					matches.add(binding);
				}
			}
		}
		return matches.toArray(new Binding[matches.size()]);
	}
	public void installCommands() {
		uninstallParserCommands();
		System.out.println("Installing Parser Commands... ");
		installParserCommands();
	}
	private void installParserCommands() {
		List<IParser> parsers = Manager.get(IParserManager.class).getParsers();
		if (parsers != null && !parsers.isEmpty()) {
			for (IParser p : parsers) {
				install(p, false);
			}
			saveBindings(bindingManager.getBindings());
		}
	}
	public void install(IParser parser) {
		install(parser,true);
	}
	private void install(IParser parser, boolean save) {
		if (parser.getKeySequence() != null && !"".equals(parser.getKeySequence().trim())) {
			install(
				new ParserCmd(parser, false),
				COMMAND_BASE_ID+parser.getName().toLowerCase().replace(" ", ""),
				"Parser "+parser.getName(),
				DEFAULT_SCHEME,
				CONTEXT_TEXT_EDITOR,
				CATEGODY_PARSER,
				"Parser",
				parser.getKeySequence(),
				save
			);
		} else {
			Activator.getInstance().warning("Cannot create command and key binding for parser " +parser.getName());
		}
		
	}
	public void uninstallParserCommands() {
		try {
			List<IParser> parsers = Manager.get(IParserManager.class).getParsers();
			if (parsers != null && !parsers.isEmpty()) {
				String commandId;
				for (IParser p : parsers) {
					commandId = COMMAND_BASE_ID+p.getName().toLowerCase().replace(" ", "");
					Command command = commandService.getCommand(commandId);
					if (command.isDefined()) {
						command.undefine();
					}
					if (cmdHandlers.containsKey(commandId)) {
						handlerService.deactivateHandler(cmdHandlers.remove(commandId));
					}
				}
				Binding[] bindings = bindingManager.getBindings();
				if (bindings != null && bindings.length > 0) {
					for (Binding binding : bindings) {
						if (
							binding.getParameterizedCommand() != null && 
							binding.getParameterizedCommand().getCommand() != null &&
							binding.getParameterizedCommand().getCommand().getId().startsWith(COMMAND_BASE_ID)
						) {
							bindingManager.removeBinding(binding);
						}
					}
					saveBindings(bindingManager.getBindings());
				}
			}
		} catch (Exception e) {
			Activator.getInstance().error("Error uninstalling command and keybinding for "+COMMAND_BASE_ID, e);
		}
	}
	public void uninstall(IParser parser) {
		uninstall("com.fimet.commands.parser."+parser.getName().toLowerCase().replace(" ", ""));
	}
	@Override
	public void uninstall(String commandId) {
		try {
			Command command = commandService.getCommand(commandId);
			if (command.isDefined()) {
				command.undefine();
			}
			if (cmdHandlers.containsKey(commandId)) {
				handlerService.deactivateHandler(cmdHandlers.remove(commandId));
			}
			Binding[] bindings = removeBindings(commandId);
			if (bindings != null && bindings.length > 0) {
				saveBindings(bindingManager.getBindings());
			}
		} catch (Exception e) {
			Activator.getInstance().error("Error uninstalling command and keybinding for "+commandId, e);
		}
	}
	@Override
	public void install(
		IHandler handler,
		String commandId,
		String commandName,
		String schemeId,
		String contextId,
		String categoryId,
		String categoryName,
		String keySequence
	)
	{
		install(handler, commandId, commandName, schemeId, contextId, categoryId, categoryName, keySequence, true);
	}
	private void install(
		IHandler handler,
		String commandId,
		String commandName,
		String schemeId,
		String contextId,
		String categoryId,
		String categoryName,
		String keySequence,
		boolean save
	)
	{
		try {
			Category category = commandService.getCategory(categoryId);
			if (!category.isDefined()) {
				category.define(categoryName, null);
			}
			Command command = commandService.getCommand(commandId);
			if (!command.isDefined()) {
				command.setHandler(handler);
				command.define(commandName, "", category);
			}
			if (!cmdHandlers.containsKey(commandId)) {
				KeySequence kseq = KeySequence.getInstance(keySequence);
				Object binds = bindingManager.getActiveBindingsDisregardingContext().get(kseq);
				if (binds == null) {
					IHandlerActivation activation = handlerService.activateHandler(commandId, handler);
					cmdHandlers.put(commandId, activation);
					ParameterizedCommand pcmd = new ParameterizedCommand(command, null);
					KeyBinding kbind = new KeyBinding(kseq,pcmd,schemeId,contextId,null,null,null,Binding.USER);
					bindingManager.addBinding(kbind);
					if (save) {
						saveBindings(bindingManager.getBindings(), schemeId);
					}
				}
			}
		} catch (Exception e) {
			Activator.getInstance().error("Error creating command and keybinding for "+commandName, e);
		}
	}
	private void saveBindings(Binding[] bindings) {
		saveBindings(bindings, DEFAULT_SCHEME);
	}
	private void saveBindings(Binding[] bindings, String schemeId) {
		try {
			Scheme activeSchema = null;
			if (bindingService.getActiveScheme() == null) {
				activeSchema = bindingManager.getScheme(schemeId);
			} else {
				activeSchema = bindingService.getActiveScheme();
			}
			if (activeSchema != null) {
				bindingService.savePreferences(activeSchema, bindingManager.getBindings());// Optimizar, gardar las preferencias es lento
			} else {
				Activator.getInstance().warning("Cannot save bindings, scheme is null");
			}
		} catch (Exception e) {
			Activator.getInstance().error("Error saving bindings", e);
		}
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {}
}
