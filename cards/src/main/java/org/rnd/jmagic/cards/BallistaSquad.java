package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ballista Squad")
@Types({Type.CREATURE})
@SubTypes({SubType.REBEL, SubType.HUMAN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BallistaSquad extends Card
{
	public static final class Ballista extends ActivatedAbility
	{
		public Ballista(GameState state)
		{
			super(state, "(X)(W), (T): Ballista Squad deals X damage to target attacking or blocking creature.");
			this.setManaCost(new ManaPool("XW"));
			this.costsTap = true;
			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), Union.instance(Attacking.instance(), Blocking.instance())), "target attacking or blocking creature");
			this.addEffect(permanentDealDamage(ValueOfX.instance(This.instance()), targetedBy(target), "Ballista Squad deals X damage to target attacking or blocking creature."));
		}
	}

	public BallistaSquad(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Ballista(state));
	}
}
