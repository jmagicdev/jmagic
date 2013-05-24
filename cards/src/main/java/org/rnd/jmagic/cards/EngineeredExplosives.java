package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Engineered Explosives")
@Types({Type.ARTIFACT})
@ManaCost("X")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({})
public final class EngineeredExplosives extends Card
{
	public static final class Explode extends ActivatedAbility
	{
		public Explode(GameState state)
		{
			super(state, "(2), Sacrifice Engineered Explosives: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Engineered Explosives.");

			this.addCost(sacrificeThis("Engineered Explosives"));
			this.setManaCost(new ManaPool("2"));

			SetGenerator nonLandPermanents = RelativeComplement.instance(Permanents.instance(), LandPermanents.instance());
			SetGenerator chargeCounters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));
			SetGenerator haveConvertedManaCost = HasConvertedManaCost.instance(chargeCounters);
			SetGenerator destroyThese = Intersect.instance(nonLandPermanents, haveConvertedManaCost);
			this.addEffect(destroy(destroyThese, "Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Engineered Explosives."));
		}
	}

	public EngineeredExplosives(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Sunburst(state));

		this.addAbility(new Explode(state));
	}
}
