package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexian Rager")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PhyrexianRager extends Card
{
	public static final class RagerTrigger extends EventTriggeredAbility
	{
		public RagerTrigger(GameState state)
		{
			super(state, "When Phrexian Rager enters the battlefield, you draw a card and you lose 1 life.");

			this.addPattern(whenThisEntersTheBattlefield());

			// TODO simultaneous
			this.addEffect(drawCards(You.instance(), 1, "You draw a card"));
			this.addEffect(loseLife(You.instance(), 1, "and you lose 1 life"));
		}
	}

	public PhyrexianRager(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new RagerTrigger(state));
	}
}
