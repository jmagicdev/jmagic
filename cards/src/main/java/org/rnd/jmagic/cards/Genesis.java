package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Genesis")
@Types({Type.CREATURE})
@SubTypes({SubType.INCARNATION})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class Genesis extends Card
{
	public static final class GenesisAbility0 extends EventTriggeredAbility
	{
		public GenesisAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, if Genesis is in your graveyard, you may pay (2)(G). If you do, return target creature card from your graveyard to your hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.triggersFromGraveyard();

			SetGenerator graveyard = GraveyardOf.instance(You.instance());
			SetGenerator inGraveyard = InZone.instance(graveyard);
			this.interveningIf = Intersect.instance(ABILITY_SOURCE_OF_THIS, inGraveyard);

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), inGraveyard), "target creature card from your graveyard"));

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (2)(G)");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)(G)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to your hand");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			move.parameters.put(EventType.Parameter.OBJECT, target);

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (2)(G). If you do, return target creature card from your graveyard to your hand.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(move));
			this.addEffect(effect);
		}
	}

	public Genesis(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of your upkeep, if Genesis is in your graveyard, you
		// may pay (2)(G). If you do, return target creature card from your
		// graveyard to your hand.
		this.addAbility(new GenesisAbility0(state));
	}
}
