package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lifebane Zombie")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ZOMBIE})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class LifebaneZombie extends Card
{
	public static final class LifebaneZombieAbility1 extends EventTriggeredAbility
	{
		public LifebaneZombieAbility1(GameState state)
		{
			super(state, "When Lifebane Zombie enters the battlefield, target opponent reveals his or her hand. You choose a green or white creature card from it and exile that card.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator hand = HandOf.instance(target);
			this.addEffect(reveal(hand, "Target opponent reveals his or her hand."));

			SetGenerator choices = Intersect.instance(HasColor.instance(Color.GREEN, Color.WHITE), HasType.instance(Type.CREATURE), InZone.instance(hand));
			this.addEffect(exile(You.instance(), choices, 1, "You choose a green or white creature card from it and exile that card."));
		}
	}

	public LifebaneZombie(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// When Lifebane Zombie enters the battlefield, target opponent reveals
		// his or her hand. You choose a green or white creature card from it
		// and exile that card.
		this.addAbility(new LifebaneZombieAbility1(state));
	}
}
