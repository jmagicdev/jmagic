package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skygames")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Skygames extends Card
{
	public static final class SkygamesAbility1 extends StaticAbility
	{
		public static final class GiveFlying extends ActivatedAbility
		{
			public GiveFlying(GameState state)
			{
				super(state, "(T): Target creature gains flying until end of turn. Activate this ability only any time you could cast a sorcery.");
				this.costsTap = true;

				SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
				this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));

				this.activateOnlyAtSorcerySpeed();
			}
		}

		public SkygamesAbility1(GameState state)
		{
			super(state, "Enchanted land has \"(T): Target creature gains flying until end of turn. Activate this ability only any time you could cast a sorcery.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), GiveFlying.class));
		}
	}

	public Skygames(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land has
		// "(T): Target creature gains flying until end of turn. Activate this ability only any time you could cast a sorcery."
		this.addAbility(new SkygamesAbility1(state));
	}
}
