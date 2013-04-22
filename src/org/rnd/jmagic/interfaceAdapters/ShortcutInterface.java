package org.rnd.jmagic.interfaceAdapters;

/**
 * An adapter interface to "filter out" questions that either have only one
 * answer (dividing a quantity over one target) or are widely seen as completely
 * irrelevant (ordering the timestamps of multiple permanents simultaneously
 * changing zones).
 */
public class ShortcutInterface extends SimplePlayerInterface
{
	enum ReplacementDeclination
	{
		NONE, LOTS, TWO, ONE;
	}

	private ReplacementDeclination replacementDeclination;

	public ShortcutInterface(org.rnd.jmagic.engine.PlayerInterface adapt)
	{
		super(adapt);
		this.replacementDeclination = ReplacementDeclination.NONE;
	}

	// We use Strings here in situations where the client wants to reference an
	// event type define on the server, but not in the client's code.
	private static final java.util.Set<String> FINAL_EVENT_TYPES;
	static
	{
		FINAL_EVENT_TYPES = new java.util.HashSet<String>();
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.PUT_INTO_HAND.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.PUT_INTO_HAND_CHOICE.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.DESTROY_PERMANENTS.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.DISCARD_CARDS.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.DISCARD_CHOICE.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.DISCARD_RANDOM.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.DRAW_CARDS.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.EXILE_CHOICE.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.MILL_CARDS.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.MOVE_CHOICE.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.MOVE_OBJECTS.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.SACRIFICE_CHOICE.toString());
		FINAL_EVENT_TYPES.add(org.rnd.jmagic.engine.EventType.SACRIFICE_PERMANENTS.toString());
	}

	@Override
	public void alertChoice(int playerID, ChooseParameters<?> choice)
	{
		if(!choice.reason.equals(ChooseReason.SPLICE_OBJECTS))
			super.alertChoice(playerID, choice);
	}

	@Override
	public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject)
	{
		switch(parameterObject.type)
		{
		case ACTIVATE_MANA_ABILITIES:
		case NORMAL_ACTIONS:
			// Things we always pass through
			return super.choose(parameterObject);

		case MOVEMENT_GRAVEYARD:
		case TIMESTAMPS:
		{
			// Things we just pick for them
			java.util.List<Integer> ret = new java.util.LinkedList<Integer>();
			for(int i = 0; i < parameterObject.choices.size(); ++i)
				ret.add(i);
			return ret;
		}

		// ... Everything else
		case COSTS:
		{
			java.util.Collection<Integer> sacrifices = new java.util.LinkedList<Integer>();
			{
				int i = 0;
				for(T choice: parameterObject.choices)
				{
					org.rnd.jmagic.sanitized.SanitizedEvent event = (org.rnd.jmagic.sanitized.SanitizedEvent)choice;
					if(FINAL_EVENT_TYPES.contains(event.type))
						sacrifices.add(i);
					i++;
				}
			}

			// Auto-order the costs with sacrifices and exiles last.
			java.util.List<Integer> ret = new java.util.LinkedList<Integer>();
			for(int i = 0; i < parameterObject.choices.size(); i++)
				if(!sacrifices.contains(i))
					ret.add(i);
			ret.addAll(sacrifices);
			return ret;
		}

		case REPLACEMENT_EFFECT:
		{
			if(this.replacementDeclination == ReplacementDeclination.NONE)
			{
				for(T choice: parameterObject.choices)
				{
					if(!((org.rnd.jmagic.sanitized.SanitizedReplacement)choice).isOptionalForMe())
					{
						this.replacementDeclination = ReplacementDeclination.NONE;
						return super.choose(parameterObject);
					}
				}

				org.rnd.jmagic.engine.PlayerInterface.ChooseParameters<T> newParams = new ChooseParameters<T>(parameterObject);
				newParams.number = new org.rnd.jmagic.engine.Set(new org.rnd.util.NumberRange(0, 1));
				java.util.List<Integer> chosen = super.choose(newParams);

				if(!chosen.isEmpty())
					return chosen;
				this.replacementDeclination = ReplacementDeclination.LOTS;
			}

			if(parameterObject.choices.size() == 2)
				this.replacementDeclination = ReplacementDeclination.TWO;
			return java.util.Collections.singletonList(0);
		}

		case ENUM:
		{
			switch(this.replacementDeclination)
			{
			case NONE:
				return super.choose(parameterObject);
			case TWO:
				this.replacementDeclination = ReplacementDeclination.ONE;
				break;
			case ONE:
				this.replacementDeclination = ReplacementDeclination.NONE;
				break;
			case LOTS:
				// don't care
			}
			return java.util.Collections.singletonList(parameterObject.choices.indexOf(org.rnd.jmagic.engine.Answer.NO));
		}

		default:
		}

		// TODO: this logic needs to be in here somewhere
		// if(!parameterObject.reason.isPublic &&
		// !parameterObject.type.isOrdered())
		// return super.choose(parameterObject);

		Integer lowerBound = org.rnd.jmagic.engine.generators.Minimum.get(parameterObject.number);
		Integer upperBound = org.rnd.jmagic.engine.generators.Maximum.get(parameterObject.number);

		java.util.List<Integer> ret = new java.util.LinkedList<Integer>();
		if(null != upperBound && 0 == upperBound && parameterObject.reason.isPublic)
			return ret;
		if((parameterObject.choices.size() == lowerBound) && ((1 == upperBound && 1 == lowerBound) || !parameterObject.type.isOrdered()))
		{
			for(int i = 0; i < parameterObject.choices.size(); ++i)
			{
				if(ret.size() == lowerBound)
					break;
				ret.add(i);
			}
			return ret;
		}

		return super.choose(parameterObject);
	}

	@Override
	public int chooseNumber(org.rnd.util.NumberRange range, String description)
	{
		Integer lower = range.getLower();
		if(lower != null && lower.equals(range.getUpper()))
			return lower;

		return super.chooseNumber(range, description);
	}

	@Override
	public void divide(int quantity, int minimum, int whatFrom, String beingDivided, java.util.List<org.rnd.jmagic.sanitized.SanitizedTarget> targets)
	{
		if(targets.size() == 1)
		{
			targets.get(0).division = quantity;
			return;
		}

		super.divide(quantity, minimum, whatFrom, beingDivided, targets);
	}
}
