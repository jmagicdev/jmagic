package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skymark Roc")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2WU")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class SkymarkRoc extends Card
{
	public static final class SkymarkRocAbility1 extends EventTriggeredAbility
	{
		public SkymarkRocAbility1(GameState state)
		{
			super(state, "Whenever Skymark Roc attacks, you may return target creature defending player controls with toughness 2 or less to its owner's hand.");
			this.addPattern(whenThisAttacks());

			SetGenerator defendingPlayerControls = ControlledBy.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS));
			SetGenerator toughness2OrLess = HasToughness.instance(Between.instance(null, 2));
			SetGenerator restriction = Intersect.instance(CreaturePermanents.instance(), defendingPlayerControls, toughness2OrLess);

			SetGenerator target = targetedBy(this.addTarget(restriction, "target creature defending player controls with toughness 2 or less"));
			EventFactory bounce = bounce(target, "Return target creature defending player controls with toughness 2 or less to its owner's hand");
			this.addEffect(youMay(bounce, "You may return target creature defending player controls with toughness 2 or less to its owner's hand."));
		}
	}

	public SkymarkRoc(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Skymark Roc attacks, you may return target creature
		// defending player controls with toughness 2 or less to its owner's
		// hand.
		this.addAbility(new SkymarkRocAbility1(state));
	}
}
