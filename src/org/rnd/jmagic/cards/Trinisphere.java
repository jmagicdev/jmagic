package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trinisphere")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Trinisphere extends Card
{
	public static final class TrinisphereAbility0 extends StaticAbility
	{
		public TrinisphereAbility0(GameState state)
		{
			super(state, "As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.");

			SetGenerator thisUntapped = Intersect.instance(Untapped.instance(), This.instance());
			this.canApply = Both.instance(this.canApply, thisUntapped);

			ContinuousEffect.Part minimum = new ContinuousEffect.Part(ContinuousEffectType.COST_MINIMUM);
			minimum.parameters.put(ContinuousEffectType.Parameter.OBJECT, Spells.instance());
			minimum.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(3));
			this.addEffectPart(minimum);
		}
	}

	public Trinisphere(GameState state)
	{
		super(state);

		// As long as Trinisphere is untapped, each spell that would cost less
		// than three mana to cast costs three mana to cast. (Additional mana in
		// the cost may be paid with any color of mana or colorless mana. For
		// example, a spell that would cost (1)(B) to cast costs (2)(B) to cast
		// instead.)
		this.addAbility(new TrinisphereAbility0(state));
	}
}
