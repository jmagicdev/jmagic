package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Aura Graft")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class AuraGraft extends Card
{
	public AuraGraft(GameState state)
	{
		super(state);

		Target target = this.addTarget(AurasEnchantingPermanents.instance(), "target Aura that's attached to a permanent");

		SetGenerator originalBearer = EnchantedBy.instance(targetedBy(target));
		SetGenerator choices = RelativeComplement.instance(Permanents.instance(), originalBearer);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

		this.addEffect(createFloatingEffect(Empty.instance(), "Gain control of target Aura that's attached to a permanent.", part));

		EventType.ParameterMap attachParameters = new EventType.ParameterMap();
		attachParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		attachParameters.put(EventType.Parameter.PLAYER, You.instance());
		attachParameters.put(EventType.Parameter.CHOICE, choices);
		attachParameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS));
		this.addEffect(new EventFactory(EventType.ATTACH_TO_CHOICE, attachParameters, "Attach it to another permanent it can enchant."));
	}
}
