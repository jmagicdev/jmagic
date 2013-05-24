package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ShoalAbility extends StaticAbility
{
	private final Color color;
	private final String cardName;

	public ShoalAbility(GameState state, Color color, String cardName)
	{
		super(state, "You may exile a " + color + " card with converted mana cost X from your hand rather than pay " + cardName + "'s mana cost.");
		this.color = color;
		this.cardName = cardName;

		SetGenerator hasColor = HasColor.instance(color);
		SetGenerator cmcX = HasConvertedManaCost.instance(ValueOfX.instance(This.instance()));
		SetGenerator fromYourHand = InZone.instance(HandOf.instance(You.instance()));

		EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, "Exile a " + color + " card with converted mana cost X from your hand");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		exileFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(hasColor, cmcX, fromYourHand));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		exileFactory.usesX();

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, exileFactory);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
		part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(altCost));
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
		this.addEffectPart(part);

		this.canApply = NonEmpty.instance();
	}

	@Override
	public ShoalAbility create(org.rnd.jmagic.engine.Game game)
	{
		return new ShoalAbility(game.physicalState, this.color, this.cardName);
	}
}