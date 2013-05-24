package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Huntmaster of the Fells")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("2RG")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.RED})
@BackFace(RavageroftheFells.class)
public final class HuntmasteroftheFells extends Card
{
	public static final class HuntmasteroftheFellsAbility0 extends EventTriggeredAbility
	{
		public HuntmasteroftheFellsAbility0(GameState state)
		{
			super(state, "Whenever this creature enters the battlefield or transforms into Huntmaster of the Fells, put a 2/2 green Wolf creature token onto the battlefield and you gain 2 life.");

			this.addPattern(whenThisEntersTheBattlefield());

			org.rnd.jmagic.engine.patterns.SimpleEventPattern pattern = new org.rnd.jmagic.engine.patterns.SimpleEventPattern(EventType.TRANSFORM_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 green Wolf creature token onto the battlefield.");
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.WOLF);
			this.addEffect(factory.getEventFactory());

			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public HuntmasteroftheFells(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever this creature enters the battlefield or transforms into
		// Huntmaster of the Fells, put a 2/2 green Wolf creature token onto the
		// battlefield and you gain 2 life.
		this.addAbility(new HuntmasteroftheFellsAbility0(state));

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Huntmaster of the Fells.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
