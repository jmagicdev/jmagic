package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ritual of the Returned")
@Types({Type.INSTANT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class RitualoftheReturned extends Card
{
	public RitualoftheReturned(GameState state)
	{
		super(state);

		// Exile target creature card from your graveyard.
		SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
		SetGenerator target = targetedBy(this.addTarget(deadThings, "Put a black Zombie creature token onto the battlefield. Its power is equal to that card's power and its toughness is equal to that card's toughness."));
		EventFactory exile = exile(target, "Exile target creature card from your graveyard.");
		this.addEffect(exile);

		// Put a black Zombie creature token onto the battlefield. Its power is
		// equal to that card's power and its toughness is equal to that card's
		// toughness.
		SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(exile));
		SetGenerator power = PowerOf.instance(thatCard);
		SetGenerator toughness = ToughnessOf.instance(thatCard);
		CreateTokensFactory zombie = new CreateTokensFactory(numberGenerator(1), power, toughness, "Put a black Zombie creature token onto the battlefield. Its power is equal to that card's power and its toughness is equal to that card's toughness.");
		zombie.setColors(Color.BLACK);
		zombie.setSubTypes(SubType.ZOMBIE);
		this.addEffect(zombie.getEventFactory());
	}
}
