package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brion Stoutarm")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.WARRIOR})
@ManaCost("2RW")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BrionStoutarm extends Card
{
	public static final class Fling extends ActivatedAbility
	{
		public Fling(GameState state)
		{
			super(state, "(R), (T), Sacrifice a creature other than Brion Stoutarm: Brion Stoutarm deals damage equal to the sacrificed creature's power to target player.");

			this.setManaCost(new ManaPool("R"));
			this.costsTap = true;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory cost = sacrifice(You.instance(), 1, RelativeComplement.instance(CreaturePermanents.instance(), thisCard), "Sacrifice a creature other than Brion Stoutarm");
			this.addCost(cost);

			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(permanentDealDamage(PowerOf.instance(OldObjectOf.instance(CostResult.instance(cost))), targetedBy(target), "Brion Stoutarm deals damage equal to the sacrificed creature's power to target player."));
		}
	}

	public BrionStoutarm(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		this.addAbility(new Fling(state));
	}
}
