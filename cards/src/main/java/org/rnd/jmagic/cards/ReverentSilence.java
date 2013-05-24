package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reverent Silence")
@Types({Type.SORCERY})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ReverentSilence extends Card
{
	public static final class Reverence extends StaticAbility
	{
		public Reverence(GameState state)
		{
			super(state, "If you control a Forest, rather than pay Reverent Silence's mana cost, you may have each other player gain 6 life.");

			EventFactory gainLife = gainLife(RelativeComplement.instance(Players.instance(), You.instance()), 6, "Each other player gains 6 life.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new CostCollection(CostCollection.TYPE_ALTERNATE, gainLife)));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			this.canApply = Both.instance(this.canApply, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.FOREST)));
		}
	}

	public ReverentSilence(GameState state)
	{
		super(state);

		// If you control a Forest, rather than pay Reverent Silence's mana
		// cost, you may have each other player gain 6 life.
		this.addAbility(new Reverence(state));

		// Destroy all enchantments.
		this.addEffect(destroy(EnchantmentPermanents.instance(), "Destroy all enchantments."));
	}
}
