package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Boros Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosKeyrune extends Card
{
	public static final class BorosKeyruneAbility1 extends ActivatedAbility
	{
		public BorosKeyruneAbility1(GameState state)
		{
			super(state, "(R)(W): Boros Keyrune becomes a 1/1 red and white Soldier artifact creature with double strike until end of turn.");
			this.setManaCost(new ManaPool("(R)(W)"));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 1, 1);
			animate.addColor(Color.RED);
			animate.addColor(Color.WHITE);
			animate.addSubType(SubType.SOLDIER);
			animate.addType(Type.CREATURE);
			animate.addAbility(org.rnd.jmagic.abilities.keywords.DoubleStrike.class);
			this.addEffect(createFloatingEffect("Boros Keyrune becomes a 1/1 red and white Soldier artifact creature with double strike until end of turn.", animate.getParts()));
		}
	}

	public BorosKeyrune(GameState state)
	{
		super(state);

		// (T): Add (R) or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RW)"));

		// (R)(W): Boros Keyrune becomes a 1/1 red and white Soldier artifact
		// creature with double strike until end of turn. (It deals both
		// first-strike and regular combat damage.)
		this.addAbility(new BorosKeyruneAbility1(state));
	}
}
