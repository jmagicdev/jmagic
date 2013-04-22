package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pain Kami")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class PainKami extends Card
{
	public static final class PainKamiAbility0 extends ActivatedAbility
	{
		public PainKamiAbility0(GameState state)
		{
			super(state, "(X)(R), Sacrifice Pain Kami: Pain Kami deals X damage to target creature.");
			this.setManaCost(new ManaPool("(X)(R)"));
			this.addCost(sacrificeThis("Pain Kami"));

			Target t = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(permanentDealDamage(ValueOfX.instance(This.instance()), targetedBy(t), "Pain Kami deals X damage to target creature."));
		}
	}

	public PainKami(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (X)(R), Sacrifice Pain Kami: Pain Kami deals X damage to target
		// creature.
		this.addAbility(new PainKamiAbility0(state));
	}
}
