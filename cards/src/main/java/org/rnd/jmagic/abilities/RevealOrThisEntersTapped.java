package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RevealOrThisEntersTapped extends StaticAbility
{
	private String cardName;
	private SubType type;

	public RevealOrThisEntersTapped(GameState state, String cardName, SubType type)
	{
		super(state, effectName(cardName, type));
		this.cardName = cardName;
		this.type = type;

		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, effectName(cardName, type));
		replacement.addPattern(asThisEntersTheBattlefield());

		SetGenerator zoneChange = replacement.replacedByThis();
		SetGenerator elementals = HasSubType.instance(type);
		SetGenerator revealable = Intersect.instance(InZone.instance(HandOf.instance(You.instance())), elementals);

		EventFactory tapped = new EventFactory(EventType.TAP_PERMANENTS, cardName + " enters the battlefield tapped");
		tapped.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
		tapped.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));

		String typeString = typeString(type);
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal " + typeString + " card from your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, revealable);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());

		EventFactory ite = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may reveal " + typeString + " card from your hand. If you don't, " + cardName + " enters the battlefield tapped.");
		ite.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(reveal, "You may reveal " + typeString + " card from your hand")));
		ite.parameters.put(EventType.Parameter.ELSE, Identity.instance(tapped));
		replacement.addEffect(ite);

		this.addEffectPart(replacementEffectPart(replacement));

		this.canApply = NonEmpty.instance();
	}

	@Override
	public RevealOrThisEntersTapped create(Game game)
	{
		return new RevealOrThisEntersTapped(game.physicalState, this.cardName, this.type);
	}

	private static String typeString(SubType type)
	{
		return (type.toString().matches("[aeiouAEIOU].*") ? "an " : "a ") + type;
	}

	private static String effectName(String cardName, SubType type)
	{
		return "As " + cardName + " enters the battlefield, you may reveal " + typeString(type) + " card from your hand. If you don't, " + cardName + " enters the battlefield tapped.";
	}
}
