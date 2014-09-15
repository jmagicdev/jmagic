package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Herald of Torment")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class HeraldofTorment extends Card
{
	public static final class HeraldofTormentAbility2 extends EventTriggeredAbility
	{
		public HeraldofTormentAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, you lose 1 life.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(loseLife(You.instance(), 1, "You lose 1 life."));
		}
	}

	public static final class HeraldofTormentAbility3 extends StaticAbility
	{
		public HeraldofTormentAbility3(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3 and has flying.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +3, +3));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public HeraldofTorment(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Bestow (3)(B)(B) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(3)(B)(B)"));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, you lose 1 life.
		this.addAbility(new HeraldofTormentAbility2(state));

		// Enchanted creature gets +3/+3 and has flying.
		this.addAbility(new HeraldofTormentAbility3(state));
	}
}
