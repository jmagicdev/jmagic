package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CreateEmblem extends EventType
{
	public static final EventType INSTANCE = new CreateEmblem();

	private CreateEmblem()
	{
		super("CREATE_EMBLEM");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// 113.2. An effect that creates an emblem is written
		// "[Player] gets an emblem with [ability]." This means that
		// [player] puts an emblem with [ability] into the command zone. The
		// emblem is both owned and controlled by that player.
		int owner = parameters.get(Parameter.CONTROLLER).getOne(Player.class).ID;

		java.util.Set<Class<? extends Keyword>> keywords = null;
		java.util.Set<Class<? extends NonStaticAbility>> nonStaticAbilities = null;
		java.util.Set<Class<? extends StaticAbility>> staticAbilities = null;

		if(parameters.containsKey(Parameter.ABILITY))
		{
			Set abilities = parameters.get(Parameter.ABILITY);
			keywords = abilities.getAllClasses(Keyword.class);
			nonStaticAbilities = abilities.getAllClasses(NonStaticAbility.class);
			staticAbilities = abilities.getAllClasses(StaticAbility.class);
		}

		Emblem e = new Emblem(game.physicalState);

		Class<?>[] constructorTypes = new Class<?>[] {GameState.class};
		Object[] constructorArguments = new Object[] {game.physicalState};
		if(null != keywords)
			for(Class<? extends Keyword> c: keywords)
				e.addAbility(org.rnd.util.Constructor.construct(c, constructorTypes, constructorArguments));
		if(null != nonStaticAbilities)
			for(Class<? extends NonStaticAbility> c: nonStaticAbilities)
				e.addAbility(org.rnd.util.Constructor.construct(c, constructorTypes, constructorArguments));
		if(null != staticAbilities)
			for(Class<? extends StaticAbility> c: staticAbilities)
				e.addAbility(org.rnd.util.Constructor.construct(c, constructorTypes, constructorArguments));

		e.ownerID = e.controllerID = owner;
		game.physicalState.commandZone().addToTop(e);

		event.setResult(Identity.instance(e));
		return true;
	}
}