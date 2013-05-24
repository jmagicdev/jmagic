package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rakdos Charm")
@Types({Type.INSTANT})
@ManaCost("BR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosCharm extends Card
{
	public RakdosCharm(GameState state)
	{
		super(state);

		// Choose one \u2014 Exile all cards from target player's graveyard;
		SetGenerator targetPlayer = targetedBy(this.addTarget(1, Players.instance(), "target player"));
		this.addEffect(1, exile(InZone.instance(GraveyardOf.instance(targetPlayer)), "Exile all cards from target player's graveyard"));

		// or destroy target artifact;
		SetGenerator targetArtifact = targetedBy(this.addTarget(2, ArtifactPermanents.instance(), "target artifact"));
		this.addEffect(2, destroy(targetArtifact, "destroy target artifact"));

		// or each creature deals 1 damage to its controller.
		DynamicEvaluation eachCreature = DynamicEvaluation.instance();
		EventFactory dealDamage = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "That creature deals 1 damage to its controller");
		dealDamage.parameters.put(EventType.Parameter.SOURCE, eachCreature);
		dealDamage.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		dealDamage.parameters.put(EventType.Parameter.TAKER, ControllerOf.instance(eachCreature));

		EventFactory effect = new EventFactory(FOR_EACH, "each creature deals 1 damage to its controller.");
		effect.parameters.put(EventType.Parameter.OBJECT, CreaturePermanents.instance());
		effect.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachCreature));
		effect.parameters.put(EventType.Parameter.EFFECT, Identity.instance(dealDamage));
		this.addEffect(3, effect);
	}
}
