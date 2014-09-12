package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rotfeaster Maggot")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class RotfeasterMaggot extends Card
{
	public static final class RotfeasterMaggotAbility0 extends EventTriggeredAbility
	{
		public RotfeasterMaggotAbility0(GameState state)
		{
			super(state, "When Rotfeaster Maggot enters the battlefield, exile target creature card from a graveyard. You gain life equal to that card's toughness.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target creature card from a graveyard"));

			this.addEffect(exile(target, "Exile target creature card from a graveyard."));
			this.addEffect(gainLife(You.instance(), ToughnessOf.instance(target), "You gain life equal to that card's toughness."));
		}
	}

	public RotfeasterMaggot(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// When Rotfeaster Maggot enters the battlefield, exile target creature
		// card from a graveyard. You gain life equal to that card's toughness.
		this.addAbility(new RotfeasterMaggotAbility0(state));
	}
}
