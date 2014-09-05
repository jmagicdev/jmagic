package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Acidic Slime")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class AcidicSlime extends Card
{
	public static final class CIPDestroySomething extends EventTriggeredAbility
	{
		public CIPDestroySomething(GameState state)
		{
			super(state, "When Acidic Slime enters the battlefield, destroy target artifact, enchantment, or land.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance(), LandPermanents.instance()), "target artifact, enchantment, or land");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact, enchantment, or land."));
		}
	}

	public AcidicSlime(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Deathtouch (Creatures dealt damage by this creature are destroyed.
		// You can divide this creature's combat damage among any of the
		// creatures blocking or blocked by it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// When Acidic Slime enters the battlefield, destroy target artifact,
		// enchantment, or land.
		this.addAbility(new CIPDestroySomething(state));
	}
}
