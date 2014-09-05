package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ghoultree")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.TREEFOLK})
@ManaCost("7G")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Ghoultree extends Card
{
	public static final class GhoultreeAbility0 extends StaticAbility
	{
		public GhoultreeAbility0(GameState state)
		{
			super(state, "Ghoultree costs (1) less to cast for each creature card in your graveyard.");

			ContinuousEffect.Part reduction = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			reduction.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			reduction.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("1")));
			reduction.parameters.put(ContinuousEffectType.Parameter.NUMBER, Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())))));
			this.addEffectPart(reduction);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public Ghoultree(GameState state)
	{
		super(state);

		this.setPower(10);
		this.setToughness(10);

		// Ghoultree costs (1) less to cast for each creature card in your
		// graveyard.
		this.addAbility(new GhoultreeAbility0(state));
	}
}
