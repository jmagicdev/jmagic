package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Centaur Chieftain")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.TORMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CentaurChieftain extends Card
{
	public static final class GrantCentaurRally extends StaticAbility
	{
		public static final class CentaurRally extends EventTriggeredAbility
		{
			public CentaurRally(GameState state)
			{
				super(state, "When Centaur Chieftain enters the battlefield, creatures you control get +1/+1 and gain trample until end of turn.");
				this.addPattern(whenThisEntersTheBattlefield());
				this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 and gain trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
			}
		}

		public GrantCentaurRally(GameState state)
		{
			super(state, "As long as seven or more cards are in your graveyard, Centaur Chieftain has \"When Centaur Chieftain enters the battlefield, creatures you control get +1/+1 and gain trample until end of turn.\"");

			this.addEffectPart(addAbilityToObject(This.instance(), CentaurRally.class));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public CentaurChieftain(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		this.addAbility(new GrantCentaurRally(state));
	}
}
