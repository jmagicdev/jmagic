package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Qasali Pridemage")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.WIZARD})
@ManaCost("GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class QasaliPridemage extends Card
{
	public static final class Naturalize extends ActivatedAbility
	{
		public Naturalize(GameState state)
		{
			super(state, "(1), Sacrifice Qasali Pridemage: Destroy target artifact or enchantment.");
			this.setManaCost(new ManaPool("1"));
			this.addCost(sacrificeThis("Qasali Pridemage"));
			Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment."));
		}
	}

	public QasaliPridemage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		// {1}, Sacrifice Qasali Pridemage: Destroy target artifact or
		// enchantment.
		this.addAbility(new Naturalize(state));
	}
}
