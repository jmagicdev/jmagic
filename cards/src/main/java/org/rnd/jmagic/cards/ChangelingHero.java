package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Changeling Hero")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class ChangelingHero extends Card
{
	public static final class ChampionACreature extends org.rnd.jmagic.abilities.keywords.Champion
	{
		public ChampionACreature(GameState state)
		{
			super(state, "Champion a creature");
		}

		@Override
		protected java.util.List<NonStaticAbility> createNonStaticAbilities()
		{
			java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

			ret.add(new ExileACreature(this.state));
			ret.add(new ReturnACreature(this.state));

			return ret;
		}

		public static final class ExileACreature extends ChampionExileAbility
		{
			public ExileACreature(GameState state)
			{
				super(state, "creature", HasType.instance(Type.CREATURE), ReturnACreature.class);
			}
		}

		public static final class ReturnACreature extends ChampionReturnAbility
		{
			public ReturnACreature(GameState state)
			{
				super(state, ExileACreature.class);
			}
		}
	}

	public ChangelingHero(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Changeling(state));
		this.addAbility(new ChampionACreature(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
