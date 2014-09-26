package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Puresteel Paladin")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class PuresteelPaladin extends Card
{
	public static final class PuresteelPaladinAbility0 extends EventTriggeredAbility
	{
		public PuresteelPaladinAbility0(GameState state)
		{
			super(state, "Whenever an Equipment enters the battlefield under your control, you may draw a card.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.EQUIPMENT), You.instance(), false));

			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public static final class PuresteelPaladinAbility1 extends StaticAbility
	{
		public PuresteelPaladinAbility1(GameState state)
		{
			super(state, "Metalcraft \u2014 Equipment you control have equip (0) as long as you control three or more artifacts.");

			SetGenerator equipment = HasSubType.instance(SubType.EQUIPMENT);
			SetGenerator youControl = ControlledBy.instance(You.instance());
			this.addEffectPart(addAbilityToObject(Intersect.instance(equipment, youControl), org.rnd.jmagic.abilities.keywords.Equip.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public PuresteelPaladin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever an Equipment enters the battlefield under your control, you
		// may draw a card.
		this.addAbility(new PuresteelPaladinAbility0(state));

		// Metalcraft \u2014 Equipment you control have equip (0) as long as you
		// control three or more artifacts.
		this.addAbility(new PuresteelPaladinAbility1(state));
	}
}
