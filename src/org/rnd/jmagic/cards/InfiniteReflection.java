package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Infinite Reflection")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class InfiniteReflection extends Card
{
	public static final class InfiniteReflectionAbility1 extends EventTriggeredAbility
	{
		public static final class EntersTheBattlfieldAttachedToACreature implements ZoneChangePattern
		{
			private static ZoneChangePattern PATTERN = new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), ABILITY_SOURCE_OF_THIS, false);

			@Override
			public boolean looksBackInTime()
			{
				return false;
			}

			@Override
			public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
			{
				if(!PATTERN.match(zoneChange, thisObject, state))
					return false;

				Identified newObject = state.get(zoneChange.newObjectID);
				if(newObject.isGameObject() && ((GameObject)newObject).getAttachedTo() != -1)
					return true;
				return false;
			}

		}

		public InfiniteReflectionAbility1(GameState state)
		{
			super(state, "When Infinite Reflection enters the battlefield attached to a creature, each other nontoken creature you control becomes a copy of that creature.");
			this.addPattern(new EntersTheBattlfieldAttachedToACreature());

			SetGenerator enchanted = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(CREATURES_YOU_CONTROL, Union.instance(Tokens.instance(), enchanted)));
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, enchanted);
			this.addEffect(createFloatingEffect(Empty.instance(), "Each other nontoken creature you control becomes a copy of that creature.", part));
		}
	}

	public static final class InfiniteReflectionAbility2 extends StaticAbility
	{
		public static final class CopyEnchanted extends ZoneChangeReplacementEffect
		{
			public CopyEnchanted(Game game)
			{
				super(game, "Nontoken creatures you control enter the battlefield as a copy of enchanted creature.");

				this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.CREATURE), Tokens.instance()), You.instance(), false));

				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
				part.parameters.put(ContinuousEffectType.Parameter.OBJECT, NewObjectOf.instance(this.replacedByThis()));
				part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, EnchantedBy.instance(This.instance()));
				this.addEffect(createFloatingEffect(Empty.instance(), "Nontoken creatures you control enter the battlefield as a copy of enchanted creature.", part));
			}

			@Override
			public boolean isCloneEffect()
			{
				return true;
			}
		}

		public InfiniteReflectionAbility2(GameState state)
		{
			super(state, "Nontoken creatures you control enter the battlefield as a copy of enchanted creature.");
			this.addEffectPart(replacementEffectPart(new CopyEnchanted(state.game)));
		}
	}

	public InfiniteReflection(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Infinite Reflection enters the battlefield attached to a
		// creature, each other nontoken creature you control becomes a copy of
		// that creature.
		this.addAbility(new InfiniteReflectionAbility1(state));

		// Nontoken creatures you control enter the battlefield as a copy of
		// enchanted creature.
		this.addAbility(new InfiniteReflectionAbility2(state));
	}
}
