package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Duergar Hedge-Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.DWARF})
@ManaCost("2(R/W)")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class DuergarHedgeMage extends Card
{
	public static final class DuergarHedgeMageAbility0 extends EventTriggeredAbility
	{
		public DuergarHedgeMageAbility0(GameState state)
		{
			super(state, "When Duergar Hedge-Mage enters the battlefield, if you control two or more Mountains, you may destroy target artifact.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Intersect.instance(Count.instance(Intersect.instance(HasSubType.instance(SubType.MOUNTAIN), ControlledBy.instance(You.instance()))), Between.instance(2, null));
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(youMay(destroy(target, "Destroy target artifact."), "You may destroy target artifact."));
		}
	}

	public static final class DuergarHedgeMageAbility1 extends EventTriggeredAbility
	{
		public DuergarHedgeMageAbility1(GameState state)
		{
			super(state, "When Duergar Hedge-Mage enters the battlefield, if you control two or more Plains, you may destroy target enchantment.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Intersect.instance(Count.instance(Intersect.instance(HasSubType.instance(SubType.PLAINS), ControlledBy.instance(You.instance()))), Between.instance(2, null));
			SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(youMay(destroy(target, "Destroy target enchantment."), "You may destroy target enchantment."));
		}
	}

	public DuergarHedgeMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Duergar Hedge-Mage enters the battlefield, if you control two or
		// more Mountains, you may destroy target artifact.
		this.addAbility(new DuergarHedgeMageAbility0(state));

		// When Duergar Hedge-Mage enters the battlefield, if you control two or
		// more Plains, you may destroy target enchantment.
		this.addAbility(new DuergarHedgeMageAbility1(state));
	}
}
