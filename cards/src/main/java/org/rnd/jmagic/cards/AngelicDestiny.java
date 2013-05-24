package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angelic Destiny")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class AngelicDestiny extends Card
{
	public static final class AngelicDestinyAbility1 extends StaticAbility
	{
		public AngelicDestinyAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +4/+4, has flying and first strike, and is an Angel in addition to its other types.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +4, +4));
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
			this.addEffectPart(addType(enchantedCreature, SubType.ANGEL));
		}
	}

	public static final class AngelicDestinyAbility2 extends EventTriggeredAbility
	{
		public AngelicDestinyAbility2(GameState state)
		{
			super(state, "When enchanted creature dies, return Angelic Destiny to its owner's hand.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addPattern(whenXDies(enchantedCreature));

			SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return Angelic Destiny to its owner's hand.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(owner));
			move.parameters.put(EventType.Parameter.OBJECT, FutureSelf.instance(ABILITY_SOURCE_OF_THIS));
			this.addEffect(move);
		}
	}

	public AngelicDestiny(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +4/+4, has flying and first strike, and is an
		// Angel in addition to its other types.
		this.addAbility(new AngelicDestinyAbility1(state));

		// When enchanted creature dies, return Angelic Destiny to its owner's
		// hand.
		this.addAbility(new AngelicDestinyAbility2(state));
	}
}
