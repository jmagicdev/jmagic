package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Caustic Tar")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class CausticTar extends Card
{
	public static final class ThrowTar extends ActivatedAbility
	{
		public ThrowTar(GameState state)
		{
			super(state, "(T): Target player loses 3 life.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 3, "Target player loses 3 life."));
		}
	}

	public static final class CausticTarAbility1 extends StaticAbility
	{
		public CausticTarAbility1(GameState state)
		{
			super(state, "Enchanted land has \"(T): Target player loses 3 life.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), ThrowTar.class));
		}
	}

	public CausticTar(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land has "(T): Target player loses 3 life."
		this.addAbility(new CausticTarAbility1(state));
	}
}
