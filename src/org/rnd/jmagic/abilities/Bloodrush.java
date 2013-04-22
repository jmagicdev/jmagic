package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Bloodrush extends ActivatedAbility
{
	private Class<?>[] abilities;
	private final String cardName;
	private final String effectText;
	private final String manaCost;
	private final SetGenerator power;
	private final SetGenerator toughness;

	public Bloodrush(GameState state, String manaCost, String cardName, int power, int toughness, String effectText, Class<?>... abilities)
	{
		this(state, manaCost, cardName, numberGenerator(power), numberGenerator(toughness), effectText, abilities);
	}

	public Bloodrush(GameState state, String manaCost, String cardName, SetGenerator power, SetGenerator toughness, String abilityText, Class<?>... abilities)
	{
		super(state, manaCost + ", Discard " + cardName + ": " + abilityText);
		this.setManaCost(new ManaPool(manaCost));

		EventFactory discard = new EventFactory(EventType.DISCARD_CARDS, "Discard " + cardName);
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.CARD, ABILITY_SOURCE_OF_THIS);
		discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addCost(discard);

		SetGenerator target = targetedBy(this.addTarget(Attacking.instance(), "target attacking creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, power, toughness, abilityText, abilities));

		this.abilities = abilities;
		this.cardName = cardName;
		this.effectText = abilityText;
		this.manaCost = manaCost;
		this.power = power;
		this.toughness = toughness;

		this.activateOnlyFromHand();
	}

	@Override
	public Bloodrush create(Game game)
	{
		return new Bloodrush(game.physicalState, this.manaCost, this.cardName, this.power, this.toughness, this.effectText, this.abilities);
	}
}
