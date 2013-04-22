package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grim Discovery")
@Types({Type.SORCERY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GrimDiscovery extends Card
{
	public GrimDiscovery(GameState state)
	{
		super(state);

		// Choose one or both \u2014
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, 2)));

		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
		SetGenerator inYourYard = InZone.instance(yourGraveyard);
		SetGenerator yourHand = HandOf.instance(You.instance());

		// Return target creature card from your graveyard to your hand;
		{
			SetGenerator creaturesInYourYard = Intersect.instance(HasType.instance(Type.CREATURE), inYourYard);
			Target target = this.addTarget(1, creaturesInYourYard, "target creature card from your graveyard");

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to your hand");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, yourHand);
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(1, move);
		}

		// and/or return target land card from your graveyard to your hand.
		{
			SetGenerator landsInYourYard = Intersect.instance(HasType.instance(Type.LAND), inYourYard);
			Target target = this.addTarget(2, landsInYourYard, "target land card from your graveyard");

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target land card from your graveyard to your hand.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, yourHand);
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(2, move);
		}
	}
}
