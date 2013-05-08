package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disciple of Bolas")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DiscipleofBolas extends Card
{
	public static final class DiscipleofBolasAbility0 extends EventTriggeredAbility
	{
		public DiscipleofBolasAbility0(GameState state)
		{
			super(state, "When Disciple of Bolas enters the battlefield, sacrifice another creature. You gain X life and draw X cards, where X is that creature's power.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory sacrifice = sacrifice(You.instance(), 1, RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS), "Sacrifice another creature");
			this.addEffect(sacrifice);

			SetGenerator X = PowerOf.instance(OldObjectOf.instance(EffectResult.instance(sacrifice)));

			this.addEffect(gainLife(You.instance(), X, "You gain X life"));
			this.addEffect(drawCards(You.instance(), X, "and draw X cards"));
		}
	}

	public DiscipleofBolas(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Disciple of Bolas enters the battlefield, sacrifice another
		// creature. You gain X life and draw X cards, where X is that
		// creature's power.
		this.addAbility(new DiscipleofBolasAbility0(state));
	}
}
