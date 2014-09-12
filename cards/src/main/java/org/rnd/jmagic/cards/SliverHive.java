package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sliver Hive")
@Types({Type.LAND})
@ColorIdentity({})
public final class SliverHive extends Card
{
	public static final class SliverHiveAbility1 extends ActivatedAbility
	{
		public SliverHiveAbility1(GameState state)
		{
			super(state, "(T): Add one mana of any color to your mana pool. Spend this mana only to cast a Sliver spell.");
			this.costsTap = true;

			EventFactory mana = new EventFactory(ADD_RESTRICTED_MANA, "Add one mana of any color to your mana pool. Spend this mana only to cast a Sliver spell.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mana.parameters.put(EventType.Parameter.TYPE, Identity.instance(new SubTypePattern(SubType.SLIVER)));
			mana.parameters.put(EventType.Parameter.PERMANENT, Empty.instance());
			mana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(Color.allColors()));

		}
	}

	public static final class SliverHiveAbility2 extends ActivatedAbility
	{
		public SliverHiveAbility2(GameState state)
		{
			super(state, "(5), (T): Put a 1/1 colorless Sliver creature token onto the battlefield. Activate this ability only if you control a Sliver.");
			this.setManaCost(new ManaPool("(5)"));
			this.costsTap = true;

			CreateTokensFactory sliver = new CreateTokensFactory(1, 1, 1, "Put a 1/1 colorless Sliver creature token onto the battlefield.");
			sliver.setSubTypes(SubType.SLIVER);
			this.addEffect(sliver.getEventFactory());

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public SliverHive(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Add one mana of any color to your mana pool. Spend this mana
		// only to cast a Sliver spell.
		this.addAbility(new SliverHiveAbility1(state));

		// (5), (T): Put a 1/1 colorless Sliver creature token onto the
		// battlefield. Activate this ability only if you control a Sliver.
		this.addAbility(new SliverHiveAbility2(state));
	}
}
