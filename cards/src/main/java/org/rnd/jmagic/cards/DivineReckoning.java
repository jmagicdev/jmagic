package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Divine Reckoning")
@Types({Type.SORCERY})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class DivineReckoning extends Card
{
	public static final PlayerInterface.ChooseReason CHOOSE_REASON = new PlayerInterface.ChooseReason("Divine Reckoning", "Choose a creature you control", true);

	public DivineReckoning(GameState state)
	{
		super(state);

		// Each player chooses a creature he or she controls.
		DynamicEvaluation player = DynamicEvaluation.instance();
		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Chooses a creature he or she controls.");
		choose.parameters.put(EventType.Parameter.PLAYER, player);
		choose.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(player)));
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, CHOOSE_REASON));

		EventFactory forEach = new EventFactory(FOR_EACH_PLAYER, "Each player chooses a creature he or she controls.");
		forEach.parameters.put(EventType.Parameter.PLAYER, Players.instance());
		forEach.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
		forEach.parameters.put(EventType.Parameter.EFFECT, Identity.instance(choose));
		this.addEffect(forEach);

		// Destroy the rest
		SetGenerator chosenCreatures = ForEachResult.instance(forEach, Players.instance());
		this.addEffect(destroy(RelativeComplement.instance(CreaturePermanents.instance(), chosenCreatures), "Destroy the rest."));

		// Flashback (5)(W)(W) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(W)(W)"));
	}
}
