package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Worldspine Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("8GGG")
@ColorIdentity({Color.GREEN})
public final class WorldspineWurm extends Card
{
	public static final class WorldspineWurmAbility1 extends EventTriggeredAbility
	{
		public WorldspineWurmAbility1(GameState state)
		{
			super(state, "When Worldspine Wurm dies, put three 5/5 green Wurm creature tokens with trample onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory tokens = new CreateTokensFactory(3, 5, 5, "Put three 5/5 green Wurm creature tokens with trample onto the battlefield.");
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.WURM);
			tokens.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public static final class WorldspineWurmAbility2 extends EventTriggeredAbility
	{
		public WorldspineWurmAbility2(GameState state)
		{
			super(state, "When Worldspine Wurm is put into a graveyard from anywhere, shuffle it into its owner's library.");
			this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), ABILITY_SOURCE_OF_THIS, false));

			EventFactory factory = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle it into its owner's library");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(factory);

			this.triggersFromGraveyard();
		}
	}

	public WorldspineWurm(GameState state)
	{
		super(state);

		this.setPower(15);
		this.setToughness(15);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Worldspine Wurm dies, put three 5/5 green Wurm creature tokens
		// with trample onto the battlefield.
		this.addAbility(new WorldspineWurmAbility1(state));

		// When Worldspine Wurm is put into a graveyard from anywhere, shuffle
		// it into its owner's library.
		this.addAbility(new WorldspineWurmAbility2(state));
	}
}
