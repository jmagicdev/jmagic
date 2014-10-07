package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Crystalline Nautilus")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.NAUTILUS})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class CrystallineNautilus extends Card
{
	public static final class CrystallineNautilusAbility1 extends EventTriggeredAbility
	{
		private final String creatureName;

		public CrystallineNautilusAbility1(GameState state)
		{
			this(state, "this creature");
		}

		public CrystallineNautilusAbility1(GameState state, String creatureName)
		{
			super(state, "When " + creatureName + " becomes the target of a spell or ability, sacrifice it.");
			this.creatureName = creatureName;
			this.addPattern(new BecomesTheTargetPattern(ABILITY_SOURCE_OF_THIS));
			this.addEffect(sacrificeThis(creatureName));
		}

		public CrystallineNautilusAbility1 create(Game game)
		{
			return new CrystallineNautilusAbility1(game.physicalState, this.creatureName);
		}
	}

	public static final class CrystallineNautilusAbility2 extends StaticAbility
	{
		public CrystallineNautilusAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +4/+4 and has \"When this creature becomes the target of a spell or ability, sacrifice it.\"");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +4, +4));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), CrystallineNautilusAbility1.class));
		}
	}

	public CrystallineNautilus(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Bestow (3)(U)(U) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(3)(U)(U)"));

		// When Crystalline Nautilus becomes the target of a spell or ability,
		// sacrifice it.
		this.addAbility(new CrystallineNautilusAbility1(state, this.getName()));

		// Enchanted creature gets +4/+4 and has
		// "When this creature becomes the target of a spell or ability, sacrifice it."
		this.addAbility(new CrystallineNautilusAbility2(state));
	}
}
