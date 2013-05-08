package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Wooden Stake")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({})
public final class WoodenStake extends Card
{
	public static final class WoodenStakeAbility0 extends StaticAbility
	{
		public WoodenStakeAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+0.");

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, +0));
		}
	}

	public static final class WoodenStakeAbility1 extends EventTriggeredAbility
	{
		public WoodenStakeAbility1(GameState state)
		{
			super(state, "Whenever equipped creature blocks or becomes blocked by a Vampire, destroy that creature. It can't be regenerated.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			SetGenerator vampires = HasSubType.instance(SubType.VAMPIRE);

			SimpleEventPattern blocks = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			blocks.put(EventType.Parameter.OBJECT, equippedCreature);
			blocks.put(EventType.Parameter.ATTACKER, vampires);
			this.addPattern(blocks);

			SimpleEventPattern becomesBlocked = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			becomesBlocked.put(EventType.Parameter.OBJECT, vampires);
			becomesBlocked.put(EventType.Parameter.ATTACKER, equippedCreature);
			this.addPattern(becomesBlocked);

			SetGenerator trigger = TriggerEvent.instance(This.instance());
			this.addEffects(bury(this, RelativeComplement.instance(Union.instance(EventParameter.instance(trigger, EventType.Parameter.OBJECT), EventParameter.instance(trigger, EventType.Parameter.ATTACKER)), equippedCreature), ""));
		}
	}

	public WoodenStake(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0.
		this.addAbility(new WoodenStakeAbility0(state));

		// Whenever equipped creature blocks or becomes blocked by a Vampire,
		// destroy that creature. It can't be regenerated.
		this.addAbility(new WoodenStakeAbility1(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
