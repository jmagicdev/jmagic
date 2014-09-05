package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("No Rest for the Wicked")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class NoRestfortheWicked extends Card
{
	public static final class HistoryRepeats extends ActivatedAbility
	{
		public HistoryRepeats(GameState state)
		{
			super(state, "Sacrifice No Rest for the Wicked: Return to your hand all creature cards that were put into your graveyard from the battlefield this turn.");

			this.addCost(sacrificeThis("No Rest for the Wicked"));

			// creature cards
			SetGenerator creatures = HasType.instance(Type.CREATURE);

			// your
			SetGenerator controller = You.instance();
			SetGenerator youOwn = OwnedBy.instance(controller);

			// put into graveyard from battlefield
			state.ensureTracker(new PutIntoGraveyardsFromBattlefieldThisTurn.DeathTracker());
			SetGenerator diedThisTurn = PutIntoGraveyardsFromBattlefieldThisTurn.instance();

			SetGenerator returnToHand = Intersect.instance(Intersect.instance(creatures, youOwn), diedThisTurn);

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(EventType.Parameter.CAUSE, This.instance());
			moveParameters.put(EventType.Parameter.OBJECT, returnToHand);
			moveParameters.put(EventType.Parameter.TO, HandOf.instance(controller));
			this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, moveParameters, "Return to your hand all creature cards that were put into your graveyard from the battlefield this turn"));
		}
	}

	public NoRestfortheWicked(GameState state)
	{
		super(state);

		this.addAbility(new HistoryRepeats(state));
	}
}
