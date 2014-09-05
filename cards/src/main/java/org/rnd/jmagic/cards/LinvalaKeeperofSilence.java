package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Linvala, Keeper of Silence")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class LinvalaKeeperofSilence extends Card
{
	public static final class StopCreatureAbilities extends StaticAbility
	{
		public StopCreatureAbilities(GameState state)
		{
			super(state, "Activated abilities of creatures your opponents control can't be activated.");

			SetGenerator opponentsControl = ControlledBy.instance(OpponentsOf.instance(You.instance()));
			SetGenerator yourOpponentsCreatures = Intersect.instance(CreaturePermanents.instance(), opponentsControl);

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(yourOpponentsCreatures));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public LinvalaKeeperofSilence(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Activated abilities of creatures your opponents control can't be
		// activated.
		this.addAbility(new StopCreatureAbilities(state));
	}
}
