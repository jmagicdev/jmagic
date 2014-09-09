package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quag Sickness")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class QuagSickness extends Card
{
	public static final class QuagSicknessAbility1 extends StaticAbility
	{
		public QuagSicknessAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -1/-1 for each Swamp you control.");
			SetGenerator swamps = Count.instance(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance())));
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), swamps, swamps));
		}
	}

	public QuagSickness(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -1/-1 for each Swamp you control.
		this.addAbility(new QuagSicknessAbility1(state));
	}
}
