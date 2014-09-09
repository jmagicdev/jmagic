package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Balefire Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("5RR")
@ColorIdentity({Color.RED})
public final class BalefireDragon extends Card
{
	public static final class BalefireDragonAbility1 extends EventTriggeredAbility
	{
		public BalefireDragonAbility1(GameState state)
		{
			super(state, "Whenever Balefire Dragon deals combat damage to a player, it deals that much damage to each creature that player controls.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator thatPlayer = TakerOfDamage.instance(triggerDamage);
			SetGenerator creaturesTheyControl = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(thatPlayer));

			SetGenerator thatMuch = Count.instance(triggerDamage);
			this.addEffect(permanentDealDamage(thatMuch, creaturesTheyControl, "Balefire Dragon deals that much damage to each creature that player controls."));
		}
	}

	public BalefireDragon(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Balefire Dragon deals combat damage to a player, it deals
		// that much damage to each creature that player controls.
		this.addAbility(new BalefireDragonAbility1(state));
	}
}
