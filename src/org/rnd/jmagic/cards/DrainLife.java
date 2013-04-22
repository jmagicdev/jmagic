package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Drain Life")
@Types({Type.SORCERY})
@ManaCost("X1B")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DrainLife extends Card
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

	public DrainLife(GameState state)
	{
		super(state);

		// Spend only black mana on X.
		this.addAbility(new XRestriction(state));

		// Drain Life deals X damage to target creature or player. You gain life
		// equal to the damage dealt, but not more life than the player's life
		// total before Drain Life dealt damage or the creature's toughness.

		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or players"));

		SetGenerator isPlayer = Intersect.instance(Players.instance(), target);
		SetGenerator value = IfThenElse.instance(isPlayer, LifeTotalOf.instance(target), ToughnessOf.instance(target));

		EventFactory note = new EventFactory(NOTE, "");
		note.parameters.put(EventType.Parameter.OBJECT, value);
		this.addEffect(note);

		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator amount = Minimum.instance(Union.instance(X, EffectResult.instance(note)));

		this.addEffect(spellDealDamage(X, target, "Drain Life deals X damage to target creature or player."));
		this.addEffect(gainLife(You.instance(), amount, " You gain life equal to the damage dealt, but not more life than the player's life total before Drain Life dealt damage or the creature's toughness."));
	}
}
