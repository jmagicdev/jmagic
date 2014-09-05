package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Soulsworn Spirit")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SoulswornSpirit extends Card
{
	public static final class SoulswornSpiritAbility1 extends EventTriggeredAbility
	{
		public SoulswornSpiritAbility1(GameState state)
		{
			super(state, "When Soulsworn Spirit enters the battlefield, detain target creature an opponent controls.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));

			this.addEffect(detain(target, "Detain target creature an opponent controls."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public SoulswornSpirit(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Soulsworn Spirit is unblockable.
		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));

		// When Soulsworn Spirit enters the battlefield, detain target creature
		// an opponent controls. (Until your next turn, that creature can't
		// attack or block and its activated abilities can't be activated.)
		this.addAbility(new SoulswornSpiritAbility1(state));
	}
}
