package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Leyline of Vitality")
@Types({Type.ENCHANTMENT})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class LeylineofVitality extends Card
{
	public static final class LeylineofVitalityAbility2 extends EventTriggeredAbility
	{
		public LeylineofVitalityAbility2(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, you may gain 1 life.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CREATURES_YOU_CONTROL, false));
			this.addEffect(youMay(gainLife(You.instance(), 1, "Gain 1 life."), "You may gain 1 life."));
		}
	}

	public LeylineofVitality(GameState state)
	{
		super(state);

		// If Leyline of Vitality is in your opening hand, you may begin the
		// game with it on the battlefield.
		this.addAbility(new org.rnd.jmagic.abilities.LeylineAbility(state, "Leyline of Vitality"));

		// Creatures you control get +0/+1.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, CREATURES_YOU_CONTROL, "Creatures you control", +0, +1, true));

		// Whenever a creature enters the battlefield under your control, you
		// may gain 1 life.
		this.addAbility(new LeylineofVitalityAbility2(state));
	}
}
