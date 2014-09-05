package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Blazing Torch")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class BlazingTorch extends Card
{
	public static final class UndeadFear extends StaticAbility
	{
		public UndeadFear(GameState state)
		{
			super(state, "Equipped creature can't be blocked by Vampires or Zombies.");

			ContinuousEffect.Part blockRestriction = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			SetGenerator blockingThis = Blocking.instance(EquippedBy.instance(This.instance()));
			SetGenerator undeadFolks = HasSubType.instance(SubType.VAMPIRE, SubType.ZOMBIE);
			SetGenerator restriction = Intersect.instance(blockingThis, undeadFolks);

			blockRestriction.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(blockRestriction);
		}
	}

	public static final class ThrowTorch extends ActivatedAbility
	{
		public ThrowTorch(GameState state)
		{
			super(state, "(T), Sacrifice Blazing Torch: Blazing Torch deals 2 damage to target creature or player.");

			this.costsTap = true;

			EventFactory sacrificeTorch = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice Blazing Torch");
			sacrificeTorch.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrificeTorch.parameters.put(EventType.Parameter.PLAYER, You.instance());
			sacrificeTorch.parameters.put(EventType.Parameter.PERMANENT, Granted.instance(This.instance()));
			this.addCost(sacrificeTorch);

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			EventFactory damage = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "Blazing Torch deals 2 damage to target creature or player.");
			damage.parameters.put(EventType.Parameter.SOURCE, Granted.instance(This.instance()));
			damage.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			damage.parameters.put(EventType.Parameter.TAKER, targetedBy(target));
			this.addEffect(damage);
		}
	}

	public static final class ThrowableTorch extends StaticAbility
	{
		public ThrowableTorch(GameState state)
		{
			super(state, "Equipped creature has \"(T), Sacrifice Blazing Torch: Blazing Torch deals 2 damage to target creature or player.\"");

			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), ThrowTorch.class));
		}
	}

	public BlazingTorch(GameState state)
	{
		super(state);

		this.addAbility(new UndeadFear(state));
		this.addAbility(new ThrowableTorch(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
