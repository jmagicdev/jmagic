package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.expansions.*;

@Name("Hushwing Gryff")
@Types({Type.CREATURE})
@SubTypes({SubType.HIPPOGRIFF})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Magic2015CoreSet.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class HushwingGryff extends Card
{
	public static final class HushwingGryffAbility2 extends StaticAbility
	{
		public HushwingGryffAbility2(GameState state)
		{
			super(state, "Creatures entering the battlefield don't cause abilities to trigger.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.STOP_TRIGGERED_ABILITY);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(new EventTriggeredAbilityStopper(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), false))));
			this.addEffectPart(part);
		}
	}

	public HushwingGryff(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Creatures entering the battlefield don't cause abilities to trigger.
		this.addAbility(new HushwingGryffAbility2(state));
	}
}
