package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Consume Spirit")
@Types({Type.SORCERY})
@ManaCost("X1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ConsumeSpirit extends Card
{
	public static final class XRestriction extends StaticAbility
	{
		public XRestriction(GameState state)
		{
			super(state, "Spend only black mana on X.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.X_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(ManaSymbol.ManaType.BLACK));
			this.addEffectPart(part);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public ConsumeSpirit(GameState state)
	{
		super(state);

		this.addAbility(new XRestriction(state));

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		SetGenerator X = ValueOfX.instance(This.instance());
		this.addEffect(spellDealDamage(X, targetedBy(target), "Consume Spirit deals X damage to target creature or player"));
		this.addEffect(gainLife(You.instance(), X, "and you gain X life."));
	}
}
