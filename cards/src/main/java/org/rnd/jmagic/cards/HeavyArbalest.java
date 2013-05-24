package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Heavy Arbalest")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class HeavyArbalest extends Card
{
	public static final class HeavyArbalestAbility0 extends StaticAbility
	{
		public HeavyArbalestAbility0(GameState state)
		{
			super(state, "Equipped creature doesn't untap during its controller's untap step.");

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EquippedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public static final class HeavyArbalestAbility1 extends StaticAbility
	{
		public static final class Ping2 extends ActivatedAbility
		{
			public Ping2(GameState state)
			{
				super(state, "(T): This creature deals 2 damage to target creature or player.");
				this.costsTap = true;
				SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
				this.addEffect(permanentDealDamage(2, target, "This creature deals 2 damage to target creature or player."));
			}
		}

		public HeavyArbalestAbility1(GameState state)
		{
			super(state, "Equipped creature has \"(T): This creature deals 2 damage to target creature or player.\"");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), Ping2.class));
		}
	}

	public HeavyArbalest(GameState state)
	{
		super(state);

		// Equipped creature doesn't untap during its controller's untap step.
		this.addAbility(new HeavyArbalestAbility0(state));

		// Equipped creature has
		// "(T): This creature deals 2 damage to target creature or player."
		this.addAbility(new HeavyArbalestAbility1(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
