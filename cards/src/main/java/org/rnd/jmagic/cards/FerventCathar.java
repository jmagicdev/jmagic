package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fervent Cathar")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class FerventCathar extends Card
{
	public static final class FerventCatharAbility1 extends EventTriggeredAbility
	{
		public FerventCatharAbility1(GameState state)
		{
			super(state, "When Fervent Cathar enters the battlefield, target creature can't block this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(cantBlockThisTurn(target, "Target creature can't block this turn"));
		}
	}

	public FerventCathar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Fervent Cathar enters the battlefield, target creature can't
		// block this turn.
		this.addAbility(new FerventCatharAbility1(state));
	}
}
