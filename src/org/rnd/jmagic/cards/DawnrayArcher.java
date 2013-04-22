package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dawnray Archer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ARCHER})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class DawnrayArcher extends Card
{
	public static final class Dawnray extends ActivatedAbility
	{
		public Dawnray(GameState state)
		{
			super(state, "(W), (T): Dawnray Archer deals 1 damage to target attacking or blocking creature.");

			this.setManaCost(new ManaPool("1W"));

			this.costsTap = true;

			Target target = this.addTarget(Union.instance(Attacking.instance(), Blocking.instance()), "target attacking or blocking creature");

			this.addEffect(permanentDealDamage(1, targetedBy(target), "Dawnray Archer deals 1 damage to target attacking or blocking creature."));
		}
	}

	public DawnrayArcher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		this.addAbility(new Dawnray(state));
	}
}
