package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Elephant Grass")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Visions.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ElephantGrass extends Card
{
	public static final class ElephantGrassAbility1 extends StaticAbility
	{
		public ElephantGrassAbility1(GameState state)
		{
			super(state, "Black creatures can't attack you.");

			SetGenerator restriction = Intersect.instance(HasColor.instance(Color.BLACK), Attacking.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public static final class ElephantGrassAbility2 extends StaticAbility
	{
		public ElephantGrassAbility2(GameState state)
		{
			super(state, "Nonblack creatures can't attack you unless their controller pays (2) for each creature he or she controls that's attacking you.");

			SetGenerator nonblackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(nonblackCreatures));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);

		}
	}

	public ElephantGrass(GameState state)
	{
		super(state);

		// Cumulative upkeep (1) (At the beginning of your upkeep, put an age
		// counter on this permanent, then sacrifice it unless you pay its
		// upkeep cost for each age counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.CumulativeUpkeep.ForMana(state, "(1)"));

		// Black creatures can't attack you.
		this.addAbility(new ElephantGrassAbility1(state));

		// Nonblack creatures can't attack you unless their controller pays (2)
		// for each creature he or she controls that's attacking you.
		this.addAbility(new ElephantGrassAbility2(state));
	}
}
